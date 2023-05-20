package br.com.openbank.service.pix.request;

import lombok.Data;

@Data
public class PixTransferRequest {
    public Integer typeKeyPix;
    public String keyPix;
    public Double value;
}
