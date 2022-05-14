package backend.window.main.bar.listener;

import backend.reader.Request;
import backend.window.main.form.FormData;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExportAttachedRequestIEcpTest {

    @Test
    void compareFields_JP_full() throws Exception {
        Request request = new Request(new File("src\\test\\resources\\requests\\cfg_JP_full.req"));
        FormData data = FormData.generateOnRequestID(161424);
        assertTrue(new ExportAttachedRequestIEcp().compareFields(request, data, null, null));
    }

    @Test
    void compareFields_JP() throws Exception {
        Request request = new Request(new File("src\\test\\resources\\requests\\cfg_JP.req"));
        FormData data = FormData.generateOnRequestID(161423);
        assertTrue(new ExportAttachedRequestIEcp().compareFields(request, data, null, null));
    }

    @Test
    void compareFields_NP_full() throws Exception {
        Request request = new Request(new File("src\\test\\resources\\requests\\cfg_NP_full.req"));
        FormData data = FormData.generateOnRequestID(161420);
        assertTrue(new ExportAttachedRequestIEcp().compareFields(request, data, null, null));
    }

    @Test
    void compareFields_NP() throws Exception {
        Request request = new Request(new File("src\\test\\resources\\requests\\cfg_NP.req"));
        FormData data = FormData.generateOnRequestID(161419);
        assertTrue(new ExportAttachedRequestIEcp().compareFields(request, data, null, null));
    }
}
