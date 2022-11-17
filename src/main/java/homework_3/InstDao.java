package homework_3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InstDao implements AutoCloseable {
    private final Session session;

    public InstDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public void addUser(String userName, String userPassword) {
        User user = new User(userName, userPassword);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.clear();
    }

    public User getUserById(int userId) {
        return session.get(User.class, userId);
    }

    public void addPost(String text, Integer userId) {
        User user = session.get(User.class, userId);
        if (user != null) {
            session.beginTransaction();
            Post post = new Post(text, user);
            session.save(post);
            session.getTransaction().commit();
            session.clear();
        } else System.out.println("Пользователь с данным Id не найден");
    }

    public void addComment(String text, Integer userId, Integer postId) {
        User user = session.get(User.class, userId);
        Post post = session.get(Post.class, postId);
        if (user != null && post != null) {
            session.beginTransaction();
            Comment comment = new Comment(text, user, post);
            session.save(comment);
            session.getTransaction().commit();
            session.clear();
        } else
            System.out.println("Пользователь и/или пост с данным Id не найден");
    }

    public void initDB() {
        session.beginTransaction();
        User user = new User("Ilon Mask", "1111");
        Post post = new Post("I bought Twitter!", user);
        Comment comment = new Comment("And now I'm going to buy the Internet!", user, post);
        session.save(user);
        session.save(post);
        session.save(comment);
        session.getTransaction().commit();
        session.clear();
    }

    @Override
    public void close() throws Exception {
        if (session != null) {
            session.close();
        }
    }
}



