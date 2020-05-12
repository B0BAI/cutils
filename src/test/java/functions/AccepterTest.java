/*
 * Copyright (C) 2018 — 2019 Honerfor, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package functions;

import com.honerfor.cutils.function.Accepter;
import java.io.File;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class AccepterTest {

  @DisplayName("Expect that Accepter Should throw exception")
  @ParameterizedTest(name = "{index} => value={0}")
  @MethodSource("accepterFunctions")
  void verifyAccepterThrowsException(Accepter<?> condition) {
    Assertions.assertThrows(
        Exception.class, () -> condition.andThen(System.out::println).accept(null));
  }

  private static Stream<Arguments> accepterFunctions() {

    final Accepter<Class<?>> firstAccepter =
        value -> {
          throw new ClassCastException();
        };

    final Accepter<File> secondAccepter =
        value -> {
          throw new IllegalAccessException();
        };

    final Accepter<String> thirdAccepter =
        value -> {
          throw new NullPointerException();
        };

    return Stream.of(
        Arguments.of(firstAccepter), Arguments.of(secondAccepter), Arguments.of(thirdAccepter));
  }
}
