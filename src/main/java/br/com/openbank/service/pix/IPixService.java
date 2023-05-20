package br.com.openbank.service.pix;

import br.com.openbank.service.pix.request.PixTransferRequest;
import br.com.openbank.service.pix.response.GetPixResponse;

public interface IPixService {
    void createPix(Integer typeKeyPix) throws Exception;
    void transferByPix(PixTransferRequest pixTransferRequest) throws Exception;
    void deletePix() throws Exception;
    GetPixResponse getPix();
}
