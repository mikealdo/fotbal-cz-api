# fotbal-cz-api [![Build Status](https://travis-ci.org/mikealdo/fotbal-cz-api.svg?branch=master)](https://travis-ci.org/mikealdo/fotbal-cz-api) [![Coverage Status](https://coveralls.io/repos/mikealdo/fotbal-cz-api/badge.svg?branch=master&service=github)](https://coveralls.io/github/mikealdo/fotbal-cz-api?branch=master)



Unofficial API for fotbal.cz statistics and results

Docker in MacOs troubleshooting

Networking problem - restart docker-machine (changing network etc.)

docker-machine restart default      # Restart the environment
eval $(docker-machine env default)  # Refresh your environment settings
docker-machine regenerate-certs default