package org.mapfish.print.processor.map.north_arrow;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.mapfish.print.AbstractMapfishSpringTest;
import org.mapfish.print.TestHttpClientFactory;
import org.mapfish.print.config.Configuration;
import org.mapfish.print.config.ConfigurationFactory;
import org.mapfish.print.processor.map.NorthArrowGraphic;
import org.mapfish.print.test.util.ImageSimilarity;
import org.springframework.beans.factory.annotation.Autowired;

public class NorthArrowGraphicTest extends AbstractMapfishSpringTest {
    @Autowired
    private ConfigurationFactory configurationFactory;

    @Autowired
    private TestHttpClientFactory requestFactory;

    private Configuration config;

    @Before
    public void setup() throws IOException {
        this.config = this.configurationFactory.getConfig(getFile("config.yaml"));
    }

    @Test
    public void testCreateSvgWidthAndHeightSet() throws Exception {
        URI file = NorthArrowGraphic.create(
                new Dimension(200, 200), "file://" + getFile("NorthArrow.svg").toString(), 90.0,
                getTaskDirectory(), config, requestFactory);

        BufferedImage referenceImage = ImageSimilarity.convertFromSvg(file, 200, 200);
//        FileUtils.copyFile(new File(file), new File("/tmp/north-arrow.svg"));
//        ImageSimilarity.writeUncompressedImage(referenceImage, "/tmp/expected-north-arrow.tiff");
        new ImageSimilarity(referenceImage, 2).assertSimilarity(getFile("expected-north-arrow.tiff"), 0);
    }

    @Test
    public void testCreateSvgWidthAndHeightNotSet() throws Exception {
        URI file = NorthArrowGraphic.create(
                new Dimension(200, 200), "file://" + getFile("NorthArrow_10.svg").toString(), 90.0,
                getTaskDirectory(), config, requestFactory);

        BufferedImage referenceImage = ImageSimilarity.convertFromSvg(file, 200, 200);
//        FileUtils.copyFile(new File(file), new File("/tmp/north-arrow_10.svg"));
//        ImageSimilarity.writeUncompressedImage(referenceImage, "/tmp/expected-north-arrow_10.tiff");
        new ImageSimilarity(referenceImage, 2).assertSimilarity(getFile("expected-north-arrow_10.tiff"), 0);
    }

}