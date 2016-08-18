package cz.mikealdo.geb

import com.ofg.stub.server.AvailablePortScanner
import cz.mikealdo.Application
import geb.spock.GebSpec
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application)
@WebAppConfiguration
@DirtiesContext
@EnableAutoConfiguration(exclude = [HibernateJpaAutoConfiguration.class])
abstract class BaseBootGebUISpec extends GebSpec {

    def setupSpec() {
        System.setProperty("APP_ENV", "dev")
        new AvailablePortScanner(9000, 9100).tryToExecuteWithFreePort { int port ->
            System.setProperty("server.port", String.valueOf(port))
            System.setProperty("geb.build.baseUrl", "http://localhost:${String.valueOf(port)}")
        }
        System.setProperty("CONFIG_FOLDER", "properties")
        System.setProperty("encrypt.key", "secretEncryptKey")
    }
}
