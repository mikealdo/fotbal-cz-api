import io.codearte.accurest.dsl.GroovyDsl

GroovyDsl.make {
    request {
        method 'GET'
        url '/api/172c09d6-dd87-47df-a0b3-8efde6ac6842'
        headers {
            header 'Content-Type': 'application/vnd.cz.mikealdo.fotbal-cz-api.v1+json'
        }
        body '''\
    [{     
    }]
'''
    }
    response {
        status 200
    }
}