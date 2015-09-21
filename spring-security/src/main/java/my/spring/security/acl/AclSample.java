package my.spring.security.acl;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

/**
 * Пример реализации модуля ACL.
 * Более подробнее смотри: spring-security/references/acl/key concepts.txt
 *
 * Для работы с модулем ACL нужно внедрить дата соурс базы где планируеться разместиь ACL таблицы
 * Внедрить нужно в JdbcMutableAclService и BasicLookupStrategy.
 * Также нужно добавить в базу таблицы ACL смотри: spring-security/references/acl/ACL Schema.txt
 *
 * В заключении нужно интегрировать логику как часть протокола доступа к приложению. Как вариант, можно
 * 1. Реализовать AccessDecisionVoter или AfterInvocationProvider
 * 2. Использовать уже готовые решение  AclEntryVoter, AclEntryAfterInvocationProvider или
 *    AclEntryAfterInvocationCollectionFilteringProvider
 */
public class AclSample {

    @Inject
    private MutableAclService aclService;

    private class SomeEntry implements Serializable {

    }

    private void createAclForEnrty() {

        ObjectIdentity oi = new ObjectIdentityImpl(SomeEntry.class, 44L);
        MutableAcl acl = null;
        try {
            // Получить acl по ObjectIdentity
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException nfe) {
            // Если не нашло то создать
            acl = aclService.createAcl(oi);
        }

        // Указать униакльный идентификатор для пользователя
        Sid sid = new PrincipalSid("Samantha");

        //Указать права доступа
        Permission p = BasePermission.ADMINISTRATION;

        // Обновить и сохранить acl (позиция в списке, прва доступа, идентификатор пользователя, флаг на способ работы
        // (открывать доступ если true и закрывать при false))
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        aclService.updateAcl(acl);
    }
}
