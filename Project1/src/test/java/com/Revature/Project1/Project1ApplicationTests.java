package com.Revature.Project1;

import com.Revature.Controllers.AccountController;
import com.Revature.DAOs.ReimbursementDAO;
import com.Revature.DAOs.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Project1ApplicationTests {
	//beans to mock
	private ReimbursementDAO reimbursementDAO;
	private UserDAO userDAO;
	//other beans
	//

	/*
	ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;


	    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        app = SpringApplication.run(SocialMediaApp.class, args);
        Thread.sleep(500);
    }

     @AfterEach
    public void tearDown() throws InterruptedException {
    	Thread.sleep(500);
    	SpringApplication.exit(app);
    }
	 */


	@BeforeEach
	void setup(){
		reimbursementDAO = Mockito.mock(ReimbursementDAO.class);
		userDAO = Mockito.mock(UserDAO.class);

		//app = controller.startAPI();


	}

	@Test
	void testLogin() {
		//User user = new User();
		/*
		MOCKITO PART:

		Create user object

		Have mockito return it when finding id

		when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        Optional<Student> returnedStudent = this.studentService.findById(2);


            assertTrue(returnedStudent.isPresent());
            verify(this.studentRepository).findById(2);

            // When calling the mocked repository method
            when(studentRepository.findAll()).thenReturn(List.of(student));
            List students = this.studentService.findAll();

            // Then
            assertEquals(List.of(student), students);
            verify(this.studentRepository).findAll();
		 */


	}

}
