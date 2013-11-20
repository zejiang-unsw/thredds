package thredds.server.radar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import thredds.junit4.SpringJUnit4ParameterizedClassRunner;
import thredds.mock.web.MockTdsContextLoader;
import thredds.util.ContentType;

import java.util.Arrays;
import java.util.Collection;


/**
 * Test RadarServer sanity check
 *
 * @author caron
 * @since 11/15/13
 */

@RunWith(SpringJUnit4ParameterizedClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/WEB-INF/applicationContext-tdsConfig.xml"}, loader = MockTdsContextLoader.class)
public class TestRadarServerUrls {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @SpringJUnit4ParameterizedClassRunner.Parameters
  public static Collection<Object[]> getTestParameters() {
    return Arrays.asList(new Object[][]{
            {"/radarServer/catalog.xml"},
            {"/radarServer/nexrad/level3/IDD/stations.xml"},
    });
  }

  String path;

  public TestRadarServerUrls(String path) {
    this.path = path;
  }

  @Test
   public void cdmRemoteRequestCapabilitiesTest() throws Exception {
     RequestBuilder rb = MockMvcRequestBuilders.get(path).servletPath(path)
   				.param("req", "capabilities");

     MvcResult result = this.mockMvc.perform( rb )
               .andExpect(MockMvcResultMatchers.status().is(200))
            //   .andExpect(MockMvcResultMatchers.content().contentType(ContentType.xml.getContentHeader()))
               .andReturn();

    System.out.printf("content = %s%n", result.getResponse().getContentAsString());
   }

}
