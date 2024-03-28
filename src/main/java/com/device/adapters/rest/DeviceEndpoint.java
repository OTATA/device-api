package com.device.adapters.rest;

import com.device.adapters.rest.dto.DeviceRequest;
import com.device.adapters.rest.dto.DeviceResponse;
import com.device.domain.model.vo.Brand;
import com.device.domain.model.vo.DeviceExternalKey;
import com.device.domain.ports.in.DeviceDeleteCommand;
import com.device.domain.service.DeviceService;
import io.micronaut.http.annotation.Body;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Path("/v1/devices")
public class DeviceEndpoint {

    private final DeviceService service;

    @Inject
    public DeviceEndpoint(DeviceService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content =
        @Content(schema = @Schema(implementation = DeviceResponse.class))
    )
    public Response create(@NotNull @Body DeviceRequest request) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Creating a device");

        return Response.status(201).entity(
                DeviceResponse.from(service.create(request.toCreateCommand()))
        ).build();
    }

    @Path("/{externalKey}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
    public Response update(@NotNull @PathParam("externalKey") UUID externalKey, @NotNull @Body DeviceRequest request) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Updating a device");

        return Response.status(200).entity(
                DeviceResponse.from(service.update(request.toUpdateCommand(externalKey)))
        ).build();
    }

    @Path("/{externalKey}")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
    public Response partialUpdate(@NotNull @PathParam("externalKey") UUID externalKey, @NotNull @Body DeviceRequest request) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Updating a device {}", externalKey);

        return Response.status(200).entity(
                DeviceResponse.from(service.update(request.toPartialUpdateCommand(externalKey)))
        ).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviceResponse.class))))
    public Response findAll() {
        LoggerFactory.getLogger(this.getClass().getName()).info("Finding all devices");

        return Response.ok().entity(
                service.findAll().stream().map(device -> DeviceResponse.from(device))
        ).build();
    }

    @Path("/{externalKey}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
    public Response findByExternalKey(@NotNull @PathParam("externalKey") UUID externalKey) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Finding a device {}", externalKey);

        return Response.ok(
                DeviceResponse.from(service.findByExternalKey(DeviceExternalKey.of(externalKey)))
        ).build();
    }

    @Path("/brands/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviceResponse.class))))
    public Response findByBrand(@NotBlank @PathParam("name") String name) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Finding a device by brand");

        return Response.ok(
                service.findByBrand(Brand.of(name)).stream().map(device -> DeviceResponse.from(device))
        ).build();
    }

    @Path("/{externalKey}")
    @DELETE
    public Response delete(@NotNull @PathParam("externalKey") UUID externalKey) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Deleting a device");

        service.delete(new DeviceDeleteCommand((externalKey)));

        return Response.status(204).build();
    }
}
