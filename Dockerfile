FROM amazoncorretto:21-alpine as build

WORKDIR /device

COPY ./ .

RUN ./gradlew build --parallel -x test
RUN mv build/libs/device-1.0-all.jar device.jar

FROM amazoncorretto:21-alpine

WORKDIR /device

COPY --from=build /device/device.jar .

CMD java -jar -Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop -Dmicronaut.environments=local device.jar