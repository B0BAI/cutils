import com.honerfor.cutils.Que;
import com.honerfor.cutils.function.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test Custom Functions")
public class FunctionsTest {

    @DisplayName("Should return Boolean True based on the given conditions.")
    @ParameterizedTest(name = "{index} => value={0}")
    @MethodSource("trueConditionFunctions")
    void verifyConditionFunctionsToBeTrue(Condition condition) {
        Que.run(() -> assertTrue(condition.isMet()))
                .andRun(() -> assertFalse(condition.isNotMet()));
    }

    private static Stream<Arguments> trueConditionFunctions() {
        final Condition firstCondition = () -> true;
        final Condition secondCondition = () -> true;

        return Stream.of(
                Arguments.of(firstCondition),
                Arguments.of(secondCondition)
        );
    }


    @DisplayName("Should return Boolean False based on the given conditions.")
    @ParameterizedTest(name = "{index} => value={0}")
    @MethodSource("falseConditionFunctions")
    void verifyConditionFunctionsToBeFalse(Condition condition) {
        Que.run(() -> assertFalse(condition.isMet()))
                .andRun(() -> assertTrue(condition.isNotMet()));
    }

    private static Stream<Arguments> falseConditionFunctions() {
        final Condition firstCondition = () -> false;
        final Condition secondCondition = () -> false;

        return Stream.of(
                Arguments.of(firstCondition),
                Arguments.of(secondCondition)
        );
    }
}
