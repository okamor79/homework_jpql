package edu.logos.java.advance;

import edu.logos.java.advance.entity.City;
import edu.logos.java.advance.entity.Country;
import edu.logos.java.advance.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

      //  uploadDB(em);

        System.out.println("Task 1");
        task1(em);
        System.out.println();

        System.out.println("Task 2");
        task2(em);
        System.out.println();

        System.out.println("Task 3");
        task3(em);
        System.out.println();

        System.out.println("Task 4");
        task4(em);
        System.out.println();

        System.out.println("Task 5");
        task5(em);
        System.out.println();

        System.out.println("Task 6");
        task6(em);
        System.out.println();

        System.out.println("Task 7");
        task7(em);
        System.out.println();

        System.out.println("Task 8");
        task8(em);
        System.out.println();

        System.out.println("Task 9");
        task9(em);
        System.out.println();

        System.out.println("Task 10");
        task10(em);
        System.out.println();

        System.out.println("Task 11");
        task11(em);
        System.out.println();


//
//        CriteriaQuery<Country> cQCountry = cb.createQuery(Country.class);
//        Root<Country> rootCountry = cQCountry.from(Country.class);
//        cQCountry.select(rootCountry);
//        cQCountry.orderBy(cb.desc(rootCountry.get("id")));
//
//
//        em.createQuery(cq).getResultList().forEach(System.out::println);
//        em.createQuery(cQCountry).getResultList().forEach(System.out::println);


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
                List<City> cityList = em.createQuery("SELECT c FROM City c", City.class)
                        .getResultList();
                User user = new User();
                user.setFullName(line);
                user.setAge(new Random().nextInt(40) + 20);
                user.setCity(cityList.get(new Random().nextInt(countCity + 1) - 1));
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

    static void task1(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task2(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);
        cq.orderBy(cb.desc(root.get("id")));
        cq.select(root);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task3(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<City> cq = cb.createQuery(City.class);
        Root<City> root = cq.from(City.class);
        cq.orderBy(cb.asc(root.get("name")));
        cq.select(root);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task4(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.orderBy(cb.desc(root.get("fullName")));
        cq.select(root);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task5(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Country> cq = cb.createQuery(Country.class);
        Root<Country> root = cq.from(Country.class);
        Expression<String> cqExpLike = root.get("name");
        Predicate cqPredLike = cb.like(cqExpLike,("a%").toLowerCase());
        cq.where(cqPredLike);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task6(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<City> cq = cb.createQuery(City.class);
        Root<City> root = cq.from(City.class);
        Expression<String> exp = root.get("name");
        Predicate cqPred1 = cb.like(exp, "%n_");
        Predicate cqPred2 = cb.like(exp,"%r_");
        Predicate cqPredFinal = cb.or(cqPred1, cqPred2);
        cq.where(cqPredFinal);
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }

    static void task7(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<User> root = cq.from(User.class);
        //Expression<Integer> expAge = root.get("age");
        cq.select(cb.min(root.get("age")));
        int userAge = em.createQuery(cq).getSingleResult();
        CriteriaQuery<User> cqUser = cb.createQuery(User.class);
        root = cqUser.from(User.class);
        Expression<Integer> userExp = root.get("age");
        Predicate userPred = cb.equal(userExp, userAge);
        cqUser.where(userPred);
        em.createQuery(cqUser).getResultList().forEach(System.out::println);
    }

    static void task8(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<User> root = cq.from(User.class);
        cq.select(cb.avg(root.get("age")));
        System.out.println(em.createQuery(cq).getSingleResult());
    }

    static void task9(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        Join<User, City> userJoin = root.join("city");
        root.fetch("city");
        em.createQuery(cq).getResultList().forEach(c -> {
            System.out.println(c);
        });
    }

    static void task10(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Expression<Integer> cqUserExp = root.get("id");
        Predicate cqPredUser = cqUserExp.in(Arrays.asList(2,5,9,12,13,16));
        Join<User, City> userJoin = root.join("city");
        cq.where(cqPredUser.not());
        root.fetch("city");
        em.createQuery(cq).getResultList().forEach(c -> {
            System.out.println(c);
        });
    }

    static void task11(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, City> userCity = root.join("city");

        Join<City, Country> cityCountry = userCity.join("country");

        cq.select(root);
     //   userCity.fetch("country");
      //  root.fetch("city");
        em.createQuery(cq).getResultList().forEach(System.out::println);
    }
}
