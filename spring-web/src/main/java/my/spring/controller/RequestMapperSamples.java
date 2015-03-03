package my.spring.controller;

import my.spring.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Доступные парметры в методах RequestMapping
 *
 * ServletRequest или HttpServletRequest;
 * HttpSession (обращение к сессии может біть потоко не безопасно, что бы повзолить нескольких запросом обращаться
 *              к сессии нужно указать RequestMappingHandlerAdapter флаг synchronizeOnSession в true)
 * Locale - текщая локаль
 * InputStream/Reader
 * OutputStream/Writer
 * HttpMethod
 *  java.security.Principal - содержит авторизированных пользователей
 * @PathVariable - секторы URL запроса
 * @MatrixVariable - матричные параметры
 * @RequestParam - параметры запроса   (в JDK 1.8+ реализованна поддержка java.util.Optional,
 *                                      в анноатциях где есть аттрибут required )
 * @RequestHeader - хедеры
 * @RequestBody - доступ к телу запроса
 * @RequestPart - доступ к фаловым данных через multipart/form-data
 * HttpEntity<?>
 * java.util.Map/org.springframework.ui.Model/org.springframework.ui.ModelMap - модель что будет переданна на View
 * RedirectAttributes - временные аттрибуты используемые в случии редиректа
 * org.springframework.validation.Errors/org.springframework.validation.BindingResult - результаты валидации
 *
 *
 * Некокоректноя работа между  BindingResult и @ModelAttribute
 *          @RequestMapping(method = RequestMethod.POST)
 *          public String processSubmit(@ModelAttribute("pet") Pet pet, Model model, BindingResult result) { ... }
 * Данные пример не сработает, рабочий пример следующий:
 *          @RequestMapping(method = RequestMethod.POST)
 *          public String processSubmit(@ModelAttribute("pet") Pet pet, BindingResult result, Model model) { ... }
 *
 * Доступные возвращаемые типы
 *
 * ModelAndView - возвращает модель с аттрибутами и параметры представления
 * Model - возвращает модель с аттрибутами
 * Map - возвращает модель с аттрибутами
 * View - параметры отрбражения
 * String - поле которое может логически относиться к файлу отображение или комманде редиректа
 * void
 * @ResponseBody - возвращенный тип, будет запсиан как в тело HTTP ответа
 * HttpHeaders - вернуть ответ без тела
 * Callable<?> - можно вернять если приложение хочет вернуть ответ ассинхронно в потоке упровляемым Spring MVC.
 *
 */
@Controller("/owners/{ownerId}")
public class RequestMapperSamples {




    /**
     *Анноатция @PathVariable - используеться для связи парметры в URL и парметра в методе
     * Параметром пути, может быть любой простой тип, как Sting, double, int, Data ect.
     */
    @RequestMapping(value="/pet/{petId}", method= RequestMethod.GET)
    public String findOwner( @PathVariable String ownerId,@PathVariable( "petId" ) long petId, Model model ) {
        model.addAttribute("owner", ownerId);
        return "displayOwnerPet";
    }

    /**
     * Так же PathVariable поддерживает регулярные выражение {varName:regex}
     */
    @RequestMapping("/spring-web/{symbolicName:[a-z-]}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]}")
    public void handler( @PathVariable String symbolicName,@PathVariable String version, @PathVariable String extension ) {

    }

    /**
     * Так же поддерживаеться Ant стиль шаблонов пути.
     * Если два паттерна, имееют подобный путь, то тот что длинее считаеться более спецелезированным к примеру
     * foo/bar* длинее и более спецелезированный чем /foo/*
     * Или когда два паттерна имеют одну длину, паттерн что имеет меньше "*" считаеться более спецелезированным
     * /hotels/{hotel}  более спецелезированный чем /hotels/*
     * Паттрен /** менее спецелезирован чем любой другой
     *
     */
    @RequestMapping("/*/pets/{petId}") // или что то вроде /myPath/*.do
    public void doAnt( @PathVariable( "petId" ) String petId ) {
    }

    /**
     * Matrix Variables
     * Что бы использовать матречные параметры, нужно укзать настройку removeSemicolonContent в
     * RequestMappingHandlerMapping на false, по умолчанию true или
     *          <mvc:annotation-driven enable-matrix-variables="true"/>
     * Матричные параметы, могут быть частью любого сигмента URL, каждый матречеый параметр, разделен
     * точкой с запятой ";". К примеру
     *      /cars;color=red;year=2012
     * Имеет, несколько параметров и их значений, таких как color и year
     * Возможны множественные значение "color=red,green,blue" разделенные запятой или "color=red;color=green;color=blue"
     * повторением ключа параметра
     *
     * Если URL может содеражть матричный параметр:
     * // GET /pets/42;q=11;r=22
     */
    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public void doMatrixParam(@PathVariable( "petId" ) String petId, @MatrixVariable int q) {
        //petId == 42
        //q == 11
    }

    /**
     * Более деталезированная версия, где точно указываеться где ожидаються матричные параметры
     * // GET /owners/42;q=11/pets/21;q=22
     */

    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public void doMatrixParam( @PathVariable String ownerId, @PathVariable String petId,
                               @MatrixVariable(value = "q", pathVar = "ownerId") int q1,
                               @MatrixVariable(value = "q", pathVar = "petId") int q2,
                               @MatrixVariable(value = "qx", defaultValue = "1") int q3 ) {

        // q1 == 11
        // q2 == 22
        // q3 == 1
    }

    /**
     * Метречные параметры мржно разместить в карту
     * // GET /owners/42;q=11;r=12/pets/21;q=22;s=23

     */
    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET)
    public void doMatrixParam(@PathVariable String ownerId, @PathVariable String petId,
                              @MatrixVariable Map<String, String> matrixVars,
                              @MatrixVariable(pathVar = "petId") Map<String, String> petMatrix) {
        // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
        // petMatrixVars: ["q" : 11, "s" : 23]
    }


    /**
     * Поддерживаемые медиа типы
     * Можно указать в RequestMapping, медиа типы, которые поддеживаються для данного запроса.
     * Так же может принемать отрицательное значение к примеру consumes = "!application/json" означает
     * все кроме указанного.
     * consumes Метода, переопределяет поведение consumes Типа
     * к примеру следующие обработчик будет запущен если Context-Type будет application/json
     */
    @RequestMapping(value = "/persons", method = RequestMethod.POST, consumes = "application/json")
    public void setPerson(@RequestBody Person person, Model model ) {
        // добавить персону
    }

    /**
     * produces - параметр что отвечает за медиа тип, передаваетмый в ответе, так же как и consumes тип может быть
     * и отрецательным, что значить все кроме указанного
     */
    @RequestMapping(value = "/persons/{personId}", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public Person getPerson( @PathVariable String personId, Model model ) {
        // get person
        return null;
    }

    /**
     * Условия по параметрам и хедерам
     * Данный метод, обработает запрос только если в запросе будет параметр MyParam со значением myValue
     */
    @RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, params = "MyParam=myValue")
    public void doOnlyWithParam( @PathVariable String petId ) {
            // do some job
    }


    /**
     *  С помошью headers можно также проверять медиа типы, к примеру:
     *  "content-type=text/*" будет работать для всех text/plain и "text/html".Но рекомендованно использовать
     *  consumes,produces которые были разработаны специально для этого
     */
    @RequestMapping(value = "/pets", method = RequestMethod.GET, headers="myHeader=myValue")
    public void doOnlyWithHeader(@PathVariable String ownerId, @PathVariable String petId, Model model) {
        // implementation omitted
    }


}
