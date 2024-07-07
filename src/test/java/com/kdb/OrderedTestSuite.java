package com.kdb;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


/**
 * 이 클래스는 테스트 스위트를 정의합니다. 여기에 코드를 추가할 필요는 없습니다.
 * 
 */
@Suite
@SelectClasses({
		CodeRegistTests.class,
		HtmlRegistTests.class,
        CodeHtmlMapRegistTests.class
})
public class OrderedTestSuite {
	// 이 클래스는 테스트 스위트를 정의합니다. 여기에 코드를 추가할 필요는 없습니다.
}