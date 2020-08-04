package jpabook.jpashop;

import jpabook.jpashop.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code

        try {

            Order order1 = new Order();
            order1.setStatus(OrderStatus.ORDER);
            em.persist(order1);

            Order order2 = new Order();
            order2.setStatus(OrderStatus.ORDER);
            em.persist(order2);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOrderPrice(1000);
            orderItem1.setOrder(order1);
            em.persist(orderItem1);

            OrderItem orderItem2 = new OrderItem();
            orderItem2.setOrderPrice(2000);
            orderItem2.setOrder(order2);
            em.persist(orderItem2);

            em.flush();
            em.clear();

            String query = "select o from Order o join fetch o.orderItems";
            String query2 = "select o from Order o ";
            String query3 = "select m from Member m where m.name like '%%'";
            List<Order> result =em.createQuery(query, Order.class).getResultList();

            System.out.println(result);

            tx.commit();
        }catch (Exception e){
            tx.rollback();

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String msg = sw.toString();
            System.out.println(msg);
        } finally {
            em.close();
        }
        emf.close();

    }
}
