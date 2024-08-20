package epams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class MethodCounter implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        String basePackage = "epams"; // 특정 패키지 경로 설정
        String[] beanNames = context.getBeanDefinitionNames();
        int methodCount = 0;

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> beanClass = bean.getClass();

            // 패키지 이름이 특정 경로로 시작하는지 확인
            if (beanClass.getPackage().getName().startsWith(basePackage)) {
                Method[] methods = beanClass.getDeclaredMethods();
                methodCount += methods.length;

                System.out.println(beanClass.getName() + " - 메소드 수: " + methods.length);
            }
        }
        System.out.println("총 메소드 수: " + methodCount);
    }
}