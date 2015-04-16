package my.spring.webflow.transition;

import org.springframework.web.multipart.MultipartFile;

/**
 * Пример загрзуки файла через Spring Web Flow
 *
 * <form:form modelAttribute="fileUploadHandler" enctype="multipart/form-data">
 *     Select file: <input type="file" name="file"/>
 *     <input type="submit" name="_eventId_upload" value="Upload" />
 * </form:form>
 *
 * Для подлючения обработчика в flow:
 *
 *  <view-state id="uploadFile" model="uploadFileHandler">
 *      <var name="fileUploadHandler" class="org.springframework.webflow.samples.booking.FileUploadHandler"/>
 *      <transition on="upload" to="finish" >
 *          <evaluate expression="fileUploadHandler.processFile()"/>
 *      </transition>
 *      <transition on="cancel" to="finish" bind="false"/>
 *   </view-state>
 */
public class FileUploadHandler {

    private transient MultipartFile file;

    public void processFile() {
        //Выполнить что либо с фалом
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

