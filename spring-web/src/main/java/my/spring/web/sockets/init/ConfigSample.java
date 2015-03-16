package my.spring.web.sockets.init;

import my.spring.web.sockets.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Collection;
import java.util.Collections;

@Configurable
@EnableWebSocket // Включить поддержку web sockets
public class ConfigSample implements WebSocketConfigurer {


    public static final int BUFFER_SIZE = 8192;

    /**
     * Настройка Web Socket через ServletServerContainerFactoryBean для  Tomcat,  WildFly,  и  Glassfish
     *
     *  XML:
     *  <bean class="org.springframework...ServletServerContainerFactoryBean">
     *      <property name="maxTextMessageBufferSize" value="8192"/>
     *      <property name="maxBinaryMessageBufferSize" value="8192"/>
     *  </bean>
     *
     *  Если реализиовывть клиент, то нужно исопльзовать WebSocketContainerFactoryBean(XML),
     *  ContainerProvider.getWebSocketContainer()(Java)
     *
     *  Для Jetty
     *      Нужно сконфигурировать Jetty WebSocketServerFactory и декорировать Spring’s DefaultHandshakeHandler
     *      @Bean
     *      public DefaultHandshakeHandler handshakeHandler() {
     *          WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
     *          policy.setInputBufferSize(8192);
     *          policy.setIdleTimeout(600000);
     *          return new DefaultHandshakeHandler(ew JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
     *      }
     *
     *      XML:
     *      <bean id="handshakeHandler" class="org.springframework...DefaultHandshakeHandler">
     *          <constructor-arg ref="upgradeStrategy"/>
     *      </bean>
     *      <bean id="upgradeStrategy" class="org.springframework...JettyRequestUpgradeStrategy">
     *          <constructor-arg ref="serverFactory"/>
     *      </bean>
     *      <bean id="serverFactory" class="org.eclipse.jetty...WebSocketServerFactory">
     *          <constructor-arg>
     *              <bean class="org.eclipse.jetty...WebSocketPolicy">
     *                  <constructor-arg value="SERVER"/>
     *                  <property name="inputBufferSize" value="8092"/>
     *                  <property name="idleTimeout" value="600000"/>
     *              </bean>
     *          </constructor-arg>
     *      </bean>
     *
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize( BUFFER_SIZE );
        container.setMaxBinaryMessageBufferSize( BUFFER_SIZE );
        return container;
    }






    /**
     * XML:
     *
     *  <websocket:handlers>
     *
     *      <websocket:mapping path="/myHandler" handler="myHandler"/>
     *
     *      <websocket:handshake-interceptors>
     *          <bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor"/>
     *      </websocket:handshake-interceptors>
     *
     *  </websocket:handlers>
     *
     * <bean id="myHandler" class="org.springframework.samples.MyHandler"/>istry
     */
    @Override
    public void registerWebSocketHandlers( WebSocketHandlerRegistry webSocketHandlerRegistry ) {

        // Возможность добавить аттрибуты в web socket session
        Collection<String> attributeNames = Collections.emptyList();


            webSocketHandlerRegistry.addHandler( myHandler(), "/myHandler" )
                    .addInterceptors( new HttpSessionHandshakeInterceptor(attributeNames) );
    }

    @Bean
    public MyWebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }
}
