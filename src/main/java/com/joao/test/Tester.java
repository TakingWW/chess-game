package com.joao.test;

import java.lang.reflect.Method;
import java.lang.IllegalAccessException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;

public class Tester {
	public Tester() throws TestException {
		TestBoard test = new TestBoard();
		Class<?> classe = test.getClass();
		int failedCount = 0;

		for (Method method : classe.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Test.class)) {
				try {
					method.setAccessible(true);
					Boolean success = (Boolean) method.invoke(test);
					String message;

					if (!success) {
						System.out.println("\u001B[31mFailed\u001B[0m " + method.getName());
						failedCount++;
					} else {
						System.out.println("\u001B[32mSucced\u001B[0m " + method.getName());
					}
				} catch (IllegalAccessException e) {
					System.out.println("Could not access the method " + method.getName());
				} catch (InvocationTargetException e) {
					System.out.println("could not invocate from " + method.getName());
				}
			}
		}
		
		if (failedCount > 0) throw new TestException("" + failedCount + " test methods failed");
	}
}
