package cz.mikealdo.geb.springcloud

import cz.mikealdo.geb.MicroserviceControllerUISpec
import org.springframework.boot.test.IntegrationTest
import spock.lang.Ignore

@IntegrationTest("spring.profiles.active:dev,springCloud")
@Ignore("Still there are some issues with ids of page components")
class SpringCloudMicroserviceControllerUISpec extends MicroserviceControllerUISpec {

    @Override
    protected inputJson() {
        return [
                pl:
                        [dependencies:
                                 [collerator:[contentTypeTemplate:'',
                                              headers:[:],
                                              'load-balancer':'ROUND_ROBIN',
                                              path:'cz.mikealdo.twitter-places-collerator',
                                              required:false,
                                              stubs:'cz.mikealdo.twitter-places-collerator:stubs',
                                              version:'']
                                 ],
                         this:'cz.mikealdo.twitter-places-analyzer']
        ]
    }
}
