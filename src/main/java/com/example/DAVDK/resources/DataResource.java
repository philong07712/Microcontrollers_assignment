package com.example.DAVDK.resources;

import com.example.DAVDK.data.service.DataService;
import com.example.DAVDK.models.Data;
import com.example.DAVDK.models.Predict;
import com.example.DAVDK.models.Recommend;
import com.example.DAVDK.utils.DataManagement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {
    DataService service = DataService.getInstance();
    @GET
    public Response getData() {
        List<Data> list = service.getAllData();
        if (list != null) {
            return Response.ok().entity(list).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("/latest")
    public Response getLatestData() {
        Data data = service.getLatestData();
        if (data != null) {
            return Response.ok().entity(data).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("/predict")
    public Response getPredict() {
        int f1 = DataManagement.getInstance().getF1();
        int f8 = DataManagement.getInstance().getF8();
        int f24 = DataManagement.getInstance().getF24();
        Predict predict = new Predict(f1, f8, f24);
        return Response.ok().entity(predict).build();
    }

    @GET
    @Path("/recommend")
    public Response getRecommend() {
        String sensitive = DataManagement.getInstance().getSensitive();
        String effect = DataManagement.getInstance().getEffect();
        String caution = DataManagement.getInstance().getCaution();
        int aqi = DataManagement.getInstance().getAqi();
        Recommend recommend = new Recommend(aqi, sensitive, effect, caution);
        return Response.ok().entity(recommend).build();
    }

    @POST
    public Response addData(@FormParam("time") Timestamp time,
                               @FormParam("aqi") int aqi,
                               @FormParam("co2") double co2,
                               @FormParam("co") double co,
                               @FormParam("pm") double pm,
                               @FormParam("temperature") double temperature,
                               @FormParam("humidity") double humidity,
                               @FormParam("coAqi") int coAqi,
                               @FormParam("pmAqi") int pmAqi) {
        Data data = new Data();
        data.setTime(time);
        data.setAqi(aqi);
        data.setCo2(co2);
        data.setCo(co);
        data.setPm(pm);
        data.setTemperature(temperature);
        data.setHumidity(humidity);
        service.addData(data, coAqi, pmAqi);

        return Response.ok().build();
    }

    @PUT
    public Response setData(@FormParam("f1") int f1, @FormParam("f8") int f8,
                            @FormParam("f24") int f24) {
        DataManagement.getInstance().setF1(f1);
        DataManagement.getInstance().setF8(f8);
        DataManagement.getInstance().setF24(f24);
        return Response.ok().build();
    }
}
