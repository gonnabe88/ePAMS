package epams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class FunctionPointCounter implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        String basePackage = "epams"; // 특정 패키지 경로 설정
        String[] beanNames = context.getBeanDefinitionNames();
        int methodCount = 0;
        int jsFunctionCount = 0;

        // Spring Beans 메소드 수 카운트
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Class<?> beanClass = bean.getClass();

            // CGLIB 프록시 클래스인지 확인하고 원본 클래스로 변환
            Class<?> originalClass = beanClass;

            if (beanClass.getName().contains("$$")) {
                originalClass = beanClass.getSuperclass();
            }

            if(originalClass.getPackage() != null) {
            // 패키지 이름이 특정 경로로 시작하는지 확인
	            if (originalClass.getPackage().getName().startsWith(basePackage)) {
	                Method[] methods = originalClass.getDeclaredMethods();
	                int classMethodCount = methods.length == 0 ? 1 : methods.length; // 메소드 수가 0이면 1로 설정
	                methodCount += classMethodCount;	
	                //System.out.println(originalClass.getName() + " - Java Class 메소드 수: " + classMethodCount);
	            }
            }
        }

        // JavaScript 함수 수 카운트
        String jsDirectoryPath = "src/main/resources/static/js";
        File jsDirectory = new File(jsDirectoryPath);
        jsFunctionCount = countJavaScriptFunctions(jsDirectory);

        // 메소드와 함수의 총 수 출력
        methodCount = methodCount == 0 ? 1 : methodCount; // 총 메소드 수가 0이면 1로 설정
        jsFunctionCount = jsFunctionCount == 0 ? 1 : jsFunctionCount; // 총 함수 수가 0이면 1로 설정

        System.out.println("Java Class 메소드 수(합계) : " + methodCount);
        System.out.println("JavaScript 함수 수(합계) : " + jsFunctionCount);
    }

    /**
     * 주어진 디렉토리의 JavaScript 파일에서 함수 수를 카운트합니다.
     * 함수는 function 키워드와 const 형태의 화살표 함수를 포함합니다.
     *
     * @param directory 검사할 디렉토리
     * @return 디렉토리 내 JavaScript 파일에 포함된 함수의 총 수
     */
    private int countJavaScriptFunctions(File directory) {
        int totalFunctionCount = 0;

        if (directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".js"));

            if (files != null) {
                for (File file : files) {
                    int functionCount = countFunctionsInFile(file);
                    functionCount = functionCount == 0 ? 1 : functionCount; // 함수 수가 0이면 1로 설정
                    totalFunctionCount += functionCount;
                    //System.out.println(file.getName() + " - 함수 수: " + functionCount);
                }
            }
        }

        return totalFunctionCount;
    }

    /**
     * 주어진 JavaScript 파일에서 함수의 개수를 카운트합니다.
     *
     * @param file 검사할 JavaScript 파일
     * @return 파일 내 함수의 개수
     */
    private int countFunctionsInFile(File file) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("function ") || line.matches("const\\s+\\w+\\s*=\\s*\\(?.*=>\\s*\\{?")) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }
}
