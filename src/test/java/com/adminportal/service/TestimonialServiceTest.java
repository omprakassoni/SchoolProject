package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.Testimonial;
import com.adminportal.repository.TestimonialRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestimonialServiceTest {

	@Autowired
	private TestimonialService testiService;
	
	@MockBean
	private TestimonialRepository testiRepo;
	
	@Test
	public void saveTestimonialTest() {
		
		Testimonial testi=new Testimonial();
		testi.setDescription("sdfdsfdsfsd");
		testi.setFilePath("/someptah/");
		testi.setName("om");
		testi.setTestimonialId(1);
		testi.setOrganization("rdddj");
		testi.setVideoPath("sdfhgsdjf");
		testi.setDateAdded(ServiceUtility.getCurrentTime());
		
		when(testiRepo.save(testi)).thenReturn(testi);
		
	}
	
	@Test
	public void updateTestimonialTest() {
		
		Testimonial testi=new Testimonial();
		testi.setDescription("sdfdsfdsfsd");
		testi.setFilePath("/someptah/");
		testi.setName("om");
		testi.setTestimonialId(1);
		testi.setOrganization("rdddj");
		testi.setVideoPath("sdfhgsdjf");
		testi.setDateAdded(ServiceUtility.getCurrentTime());
		
		when(testiRepo.save(testi)).thenReturn(testi);
		
		int updateTestValue=0;
		boolean  updateTestBoolean=false;
		
		when(testiRepo.updateTestimonial("om", "dskfgdshfgsdhfgdsf", "drdo", 1)).thenReturn(updateTestValue);
		
		if(updateTestValue>0) {
			updateTestBoolean=true;
		}
		
		assertEquals(updateTestBoolean, testiService.updateTestimonial("om", "dskfgdshfgsdhfgdsf", "drdo", 1));
		
		
	}
}
