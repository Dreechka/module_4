package homework_3;

import org.hibernate.SessionFactory;

public class Runner {
    public static void main(String[] args) {
        UserDto userDto = new UserDto();
        try (SessionFactory sessionFactory = HibernateConfig.createSessionFactory();
             InstDao instDao = new InstDao(sessionFactory)) {

            instDao.initDB();
            instDao.addUser("Chuck Norris", "1111");
            instDao.addPost("I don't understand Hibernate!", 2);
            instDao.addComment("sad but true", 2, 2);

            User user = instDao.getUserById(2);
            userDto.setName(user.getName());
            userDto.setPassword(user.getPassword());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setPosts(user.getPosts());
            userDto.setComments(user.getComments());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (userDto != null) {
            System.out.println(userDto);
        }
    }
}
