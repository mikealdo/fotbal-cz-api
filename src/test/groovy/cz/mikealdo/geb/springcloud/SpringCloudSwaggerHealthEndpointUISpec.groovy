package cz.mikealdo.geb.springcloud

import cz.mikealdo.geb.SwaggerHealthEndpointUISpec
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev,springCloud")
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,HibernateJpaAutoConfiguration.class])
class SpringCloudSwaggerHealthEndpointUISpec extends SwaggerHealthEndpointUISpec {

}
