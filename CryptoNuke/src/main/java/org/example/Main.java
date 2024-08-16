package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new GUI();
    }

    static String checkBCPrice() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coincap.io/v2/assets?search=bitcoin&limit=1")).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseBody = (String)response.body();

            ObjectMapper om = new ObjectMapper();
            CoinDataList coinDataList = om.readValue(responseBody, CoinDataList.class);
            CoinData singleCoin = coinDataList.getData().get(0);
            String usdPrice = singleCoin.getPriceUsd();
            return usdPrice;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

