import com.lai.petpoint.Role
import com.lai.petpoint.User
import com.lai.petpoint.UserRole
import groovy.util.logging.Log4j

@Log4j
class BootStrap {

    def init = { servletContext ->
        log.info("Adding user")
        User user = User.findByUsername('admin')
        if (!user) {
            user = new User(passwordExpired: false, accountLocked: false, accountExpired: false, enabled: true, phone: '3143143141', password: 'admin', username: 'admin', name: 'Admin Guy', email: 'admin@admin.com')
            user.save(flush: true)

            Role role = new Role()
            role.authority = 'ROLE_USER'
            role.save(flush: true)
            role = new Role()
            role.authority = 'ROLE_ADMIN'
            role.save(flush: true)

            UserRole userRole = new UserRole()
            userRole.user = user
            userRole.role = role
            userRole.save(flush: true)
            log.info("Added admin user ${user.username} / ${user.password}")
        } else {
            log.info("Admin user found!")
        }
    }
    def destroy = {
    }
}
