package br.com.openbank.service.pix;

import br.com.openbank.service.pix.request.PixTransferRequest;

public interface IPixService {
    void createPix(Integer typeKeyPix) throws Exception;
    void transferByPix(PixTransferRequest pixTransferRequest) throws Exception;
}
