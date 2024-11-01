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
                }
            }
        }

        // JavaScript 함수 수 카운트
        String jsDirectoryPath = "src/main/resources/static/js";
        File jsDirectory = new File(jsDirectoryPath);
        jsFunctionCount = countJavaScriptFunctions(jsDirectory);

        // JavaScript 파일 수 카운트
        int jsFileCount = countFilesInDirectory(jsDirectory, ".js");

        // extentions 파일 수 카운트
        String extensionsDirectoryPath = "src/main/resources/static/extensions";
        File extensionsDirectory = new File(extensionsDirectoryPath);
        int extentionsFileCount = countFilesInDirectory(extensionsDirectory, "");

        // extentions 파일 수 카운트
        String extensionsJsDirectoryPath = "src/main/resources/static/extensions";
        File extensionsJsDirectory = new File(extensionsJsDirectoryPath);
        int extensionsJsFileCount = countFilesInDirectory(extensionsJsDirectory, ".js");

        // extentions 파일 수 카운트
        String extensionsCssDirectoryPath = "src/main/resources/static/extensions";
        File extensionsCssDirectory = new File(extensionsCssDirectoryPath);
        int extensionsCssFileCount = countFilesInDirectory(extensionsCssDirectory, ".css");

        // HTML 파일 수 카운트
        String htmlDirectoryPath = "src/main/resources/templates";
        File htmlDirectory = new File(htmlDirectoryPath);
        int htmlFileCount = countFilesInDirectory(htmlDirectory, ".html");

        // Java 파일 수 카운트
        String javaDirectoryPath = "src/main/java";
        File javaDirectory = new File(javaDirectoryPath);
        int javaFileCount = countFilesInDirectory(javaDirectory, ".java");

        // CSS 파일 수 카운트
        String cssDirectoryPath = "src/main/resources/static/css";
        File cssDirectory = new File(cssDirectoryPath);
        int cssFileCount = countFilesInDirectory(cssDirectory, "");

        // 리소스이미지 파일 수 카운트
        String imgDirectoryPath = "src/main/resources/static/images";
        File imgDirectory = new File(imgDirectoryPath);
        int imgFileCount = countFilesInDirectory(imgDirectory, "");

        // mapper 파일 수 카운트
        String mapperDirectoryPath = "src/main/resources/mapper";
        File mapperDirectory = new File(mapperDirectoryPath);
        int mapperFileCount = countFilesInDirectory(mapperDirectory, ".xml");

        // yml 파일 수 카운트
        String ymlDirectoryPath = "src/main/resources";
        File ymlDirectory = new File(ymlDirectoryPath);
        int ymlFileCount = countFilesInDirectory(ymlDirectory, ".yml");

        // 모든 파일 수 카운트(resources 전체)
        String resourcesDirectoryPath = "src/main/resources";
        File resourcesDirectory = new File(resourcesDirectoryPath);
        int resourcesFileCount = countFilesInDirectory(resourcesDirectory, "");

        // 메소드, 함수, 파일의 총 수 출력
        int sum = javaFileCount+jsFileCount+extensionsJsFileCount+htmlFileCount+extensionsCssFileCount+mapperFileCount+ymlFileCount;
        System.out.println("# 모든 프로그램(JAVA+JS+CSS+XML+YML) 수: " + sum);
        System.out.println(" - Java 파일 수: " + javaFileCount);
        System.out.println(" - JavaScript 파일 수: " + jsFileCount);
        System.out.println(" - 외부 JavaScript 파일 수: " + extensionsJsFileCount);
        System.out.println(" - CSS 파일 수: " + cssFileCount);
        System.out.println(" - 외부 CSS 파일 수: " + extensionsCssFileCount);
        System.out.println(" - HTML 파일 수: " + htmlFileCount);
        System.out.println(" - Mapper 파일 수: " + mapperFileCount);
        System.out.println(" - YML 파일 수: " + ymlFileCount);
        int fp = methodCount+jsFunctionCount;
        System.out.println("# 기능 수 : " + fp);
        System.out.println(" - Java Class 메소드 수 : " + methodCount);
        System.out.println(" - JavaScript 함수 수 : " + jsFunctionCount);        
        System.out.println("# 모든 리소스(HTML, JS, CSS 등) 파일 수: " + resourcesFileCount);
        System.out.println("# 외부 라이브러리(HTML, JS, CSS 등) 파일 수: " + extentionsFileCount);
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

    /**
     * 주어진 디렉토리에서 특정 확장자를 가진 파일의 수를 카운트합니다. 
     *
     * @param directory 검사할 디렉토리
     * @param extension 검사할 파일 확장자 (예: ".html", ".java")
     * @return 디렉토리 내 특정 확장자를 가진 파일의 총 수
     */
    private int countFilesInDirectory(File directory, String extension) {
        int fileCount = 0;

        if (directory.isDirectory()) {
            File[] files = directory.listFiles(); 

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileCount += countFilesInDirectory(file, extension);
                    } else if (file.getName().endsWith(extension)) {
                        // System.out.println(file.getName());
                        fileCount++;
                    }
                }
            }
        }

        return fileCount;
    }
}