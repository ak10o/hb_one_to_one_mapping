package com.mbrdi.hibernate_demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mbrdi.hibernate_demo.entity.Instructor;
import com.mbrdi.hibernate_demo.entity.InstructorDetail;

public class OneToOneMain {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate_config.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();
	
		// create session
		Session session = factory.getCurrentSession();

		try {
			// create a instructor object
			Instructor instructor = new Instructor("avi", "kumar", "avi@gmail.com");
			InstructorDetail aviDetail = new InstructorDetail("www.avi.com/youtube", "code");
			
			//associate the objects
			instructor.setInstructorDetail(aviDetail);
			
			// Start a transaction
			session.beginTransaction();

			// save the instructor object and associated object(InstructorDetail) coz of cascade operation.
			session.save(instructor);
			System.out.println("object is saved " + instructor.getId());
			
			Instructor removeInstructor= session.get(Instructor.class, instructor.getId());
			session.delete(removeInstructor);
			System.out.println("deleted " + removeInstructor.getId());

			// commit transaction
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}
}
