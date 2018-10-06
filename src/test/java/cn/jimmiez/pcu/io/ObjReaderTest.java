package cn.jimmiez.pcu.io;

import cn.jimmiez.pcu.model.PcuPointCloud3f;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class ObjReaderTest {

    @Test
    public void objDataReadTest() {
        ObjReader reader = new ObjReader();
        File file = new File(ObjReaderTest.class.getClassLoader().getResource("model/obj/bunny.obj").getFile());
        Map<ObjReader.ObjDataType, List<float[]>> dataMap = reader.read(file);
        assertNotNull(dataMap);
        assertTrue(dataMap.size() > 0);

    }
}
