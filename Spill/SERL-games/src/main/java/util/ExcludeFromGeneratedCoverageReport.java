package util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// https://stackoverflow.com/questions/47824761/how-would-i-add-an-annotation-to-exclude-a-method-from-a-jacoco-code-coverage-re
//
// We use this on "view" code as per
// https://discord.com/channels/1193905141632483400/1193905141649264707/1235593176744460428
// to make it a bit easier to see if we hit the goal of 75% coverage
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface ExcludeFromGeneratedCoverageReport {}
