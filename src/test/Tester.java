package test;

import java.lang.reflect.Method;
import java.lang.IllegalAccessException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;

import objects.Color;
import static objects.Util.*;

public class Tester {
	public Tester() throws TestException {
		TestBoard test = new TestBoard();
		Class<?> testClass = test.getClass();
		int failedCount = 0;

		for (Method method : testClass.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Test.class)) {
				try {
					method.setAccessible(true);
					Boolean success = (Boolean) method.invoke(test);
					String message;

					if (!success) {
						log("Failed", Color.RED, method.getName());
						failedCount++;
					} else {
						log("Succeed", Color.GREEN, method.getName());
					}
				} catch (IllegalAccessException e) {
					log("Fail", Color.RED, "Could not access the method " + method.getName());
				} catch (InvocationTargetException e) {
					log("Fail", Color.RED, "could not invocate from " + method.getName());
				}
			}
		}
		
		if (failedCount > 0) throw new TestException("" + failedCount + " test methods failed");
	}
}
