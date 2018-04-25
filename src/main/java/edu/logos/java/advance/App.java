package edu.logos.java.advance;

import edu.logos.java.advance.entity.City;
import edu.logos.java.advance.entity.Country;
import edu.logos.java.advance.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

      //  uploadDB(em);
        System.out.println();
        System.out.println("Task 1");
        em.createQuery("SELECT u FROM User u", User.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 2");
        em.createQuery("SELECT c FROM Country c ORDER BY c.id DESC", Country.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 3");
        em.createQuery("SELECT c FROM City c ORDER BY c.name", City.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 4");
        em.createQuery("SELECT u FROM User u ORDER BY u.fullName DESC", User.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 5");
        em.createQuery("SELECT c FROM Country c WHERE LOWER(c.name) like 'a%'", Country.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 6");
        em.createQuery("SELECT c FROM City c WHERE c.name like '%n_' OR c.name like '%r_'", City.class).getResultList().forEach(System.out::println);
        System.out.println();
        System.out.println("Task 7  ");
        em.createQuery("SELECT u FROM User u WHERE u.age = (SELECT MIN(uu.age) FROM User uu)", User.class).getResultList().forEach(System.out::println);
//        System.out.println(usr);
        System.out.println();
        System.out.println("Task 8");
        double u = em.createQuery("SELECT avg(u.age) FROM User u", Double.class).getSingleResult();
        System.out.println(u);
        System.out.println();
        System.out.println("Task 9");
        List<Object[]> usr2 = em.createQuery("SELECT u, c FROM User u LEFT JOIN FETCH u.cityList c")

                .getResultList();
       System.out.println(usr2);


        //usr2.getCityList().forEach(System.out::println);
//        System.out.println();
//        System.out.println("Task 10");
//        Map<User, City> usr3 = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.cityList c WHERE u.id NOT IN (2, 5, 9, 12, 13, 16)", User.class)
//                .getResultList();
//        System.out.println(usr3);


        em.getTransaction().commit();

        em.close();
        entityManagerFactory.close();
    }

    static void uploadDB(EntityManager em) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        String line;
        int countCountry = 0;
        int countCity = 0;

        File fileCountry = new File("country.txt");
        File fileCity = new File("city.txt");
        File fileUser = new File("user.txt");

        // Add records to table Country
        try {
            fr = new FileReader(fileCountry);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                countCountry += 1;
                Country country = new Country();
                country.setName(line);
                em.persist(country);
            }
        } catch (FileNotFoundException fe) {
            System.err.println("File " + fileCountry + " not exists");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fr.close();
            br.close();
        }

        // Add records to table City
        try {
            List<Country> countries = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
            fr = new FileReader(fileCity);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                countCity += 1;
                City city = new City();
                city.setName(line);
                city.setCountry(countries.get(new Random().nextInt(countCountry - 1) + 1));
                em.persist(city);
            }
        } catch (FileNotFoundException fe) {
            System.err.println("File " + fileCity + " not exists");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fr.close();
            br.close();
        }

        // Add records to table User
        try {

            fr = new FileReader(fileUser);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null ) {
                List<City> cityList = em.createQuery("SELECT c FROM City c WHERE c.id = ?1", City.class)
                        .setParameter(1, new Random().nextInt(countCity - 1) + 1)
                        .getResultList();
                User user = new User();
                user.setFullName(line);
                user.setAge(new Random().nextInt(40) + 20);
                user.setCityList(cityList);
                em.persist(user);
            }

        } catch (FileNotFoundException fe) {
            System.err.println("File " + fileUser + " not exists");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fr.close();
            br.close();
        }
    }


}
