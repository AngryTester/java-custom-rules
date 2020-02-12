package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class NameShouldNotContainChineseTest {
    @Test
    public void detected() {
        JavaCheckVerifier.verify("src/test/files/NameShouldNotContainChinese.java", new NameShouldNotContainChineseRule());
    }
}
