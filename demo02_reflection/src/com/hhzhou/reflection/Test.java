package com.hhzhou.reflection;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: hhzhou
 * @create:2020/10/27
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        // 获取类的class对象
        // 通过类的全限定名获取
        Class c1 = Class.forName("com.hhzhou.reflection.User");
        System.out.println(c1.hashCode());

        // 通过类名获取
        Class c2 = User.class;
        System.out.println(c2.hashCode());

        // 通过类的对象获取
        User user = new User();
        Class c3 = user.getClass();
        System.out.println(c3.hashCode());

        // 基本数据类型的包装类获取
        Class c4 = Integer.TYPE;

        //---------------------------------------------------------
        // 通过class对象获取类的相关信息
        // 获取类名
        System.out.println("获取类名");
        String name = c1.getName();
        System.out.println(name);

        // 获取属性
        // 获取公有属性
        System.out.println("获取公有属性");
        Field[] fields = c1.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        // 获取所有字段属性
        System.out.println("获取所有属性");
        Field[] declaredFields = c1.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

        // 获取指定字段属性
        System.out.println("获取指定字段");
        Field username = c1.getDeclaredField("username");
        System.out.println(username);

        // 获取方法
        // 获取公有方法，父类和子类
        System.out.println("获取本类和父类的公有方法");
        Method[] methods = c1.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        // 获取本类的所有方法
        System.out.println("获取本类的所有方法");
        Method[] declaredMethods = c1.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }

        // 获取构造器
        System.out.println("获取公有构造器");
        Constructor[] constructors = c1.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("获取全部构造器");
        Constructor[] declaredConstructors = c1.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }

        // 获取类加载器
        System.out.println("获取类加载器");
        ClassLoader classLoader = c1.getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());

        // 获取注解
        System.out.println("获取注解");
        Annotation[] annotations = c1.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        // 获取注解值
        MyClass annotation = (MyClass) c1.getAnnotation(MyClass.class);
        System.out.println(annotation.value());

        Field id = c1.getDeclaredField("id");
        MyField annotation1 = id.getAnnotation(MyField.class);
        System.out.println(annotation1.id());
        System.out.println(annotation1.name());


        //---------------------------------------------------------
        // 有了Class对象能做什么

        // 1. 创建类的对象
        // 通过class对象的newInstance方法(必须要有无参构造函数)
        System.out.println("创建类的对象");
        User user1 = (User) c1.newInstance();
        System.out.println(user1);

        // 通过class对象的构造器创建
        Constructor declaredConstructor = c1.getDeclaredConstructor(int.class, String.class, String.class);
        User user2 = (User) declaredConstructor.newInstance(1,"hhzhou","123456");
        System.out.println(user2);

        // 2. 调用普通方法
        System.out.println("调用普通方法");
        Method method1 = c1.getDeclaredMethod("setUsername", String.class);
        Object o2 = method1.invoke(user2, "HMY");
        System.out.println(o2);
        Method method = c1.getDeclaredMethod("getUsername");
        Object o = method.invoke(user2, null);
        System.out.println(o);
        // 3. 获取和修改属性信息
        System.out.println("获取和修改属性信息");
        Field username1 = c1.getDeclaredField("username");
        System.out.println(username1);
        // 关闭安全检查，既可以修改私有属性，又可以提高效率
        username1.setAccessible(true);
        username1.set(user2,"HMYYYYYY");
        System.out.println(username1);

    }
}

@SuppressWarnings("unchecked")
@MyClass("UserClass")
class User{
    @MyField(name = "field", id = 222)
    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface MyClass{
    String value() default "I am a Class";
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyField{
    String name() default "I am a field";
    int id() default 111;
}