/*
 * _________  ____ ______________.___.____       _________
 * \_   ___ \|    |   \__    ___/|   |    |     /   _____/
 * /    \  \/|    |   / |    |   |   |    |     \_____  \
 * \     \___|    |  /  |    |   |   |    |___  /        \
 *  \______  /______/   |____|   |___|_______ \/_______  /
 *         \/                                \/        \/
 *
 * Copyright (C) 2018 — 2022 Bobai Kato. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package art.cutils.value;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A pair of values. Two heads are better than one—sometimes.
 *
 * @param <F> first value type
 * @param <S> second value type
 * @author <a href="https://github.com/B0BAI">Bobai Kato</a>
 * @since 2.0
 */
public final class Pair<F, S> {

  /** First value. */
  private F first;
  /** Second value. */
  private S second;

  /**
   * Constructor to create a pair of values.
   *
   * @param first first value, can be null.
   * @param second second value, can be null.
   */
  @Contract(pure = true)
  private Pair(final F first, final S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Creates a pair of values.
   *
   * @param first first value, can be null.
   * @param second second value, can be null.
   * @return a pair of values
   */
  @Contract(value = "_, _ -> new", pure = true)
  public static <F, S> @NotNull Pair<F, S> of(final F first, final S second) {
    return new Pair<>(first, second);
  }

  /**
   * Create and empty pair.
   *
   * @return new instance of empty pair
   * @param <F> first value type
   * @param <S> second value type
   */
  @Contract(value = " -> new", pure = true)
  public static <F, S> @NotNull Pair<F, S> empty() {
    return new Pair<>(null, null);
  }

  /**
   * Returns the first value.
   *
   * @return the first value
   */
  @Contract(pure = true)
  public F getFirst() {
    return this.first;
  }

  /**
   * Use to set the first value.
   *
   * @param first first value
   */
  @Contract(mutates = "this")
  public void setFirst(final F first) {
    this.first = first;
  }

  /**
   * Use to set the first and second values.
   *
   * @param first first value
   * @param second second value
   */
  @Contract(mutates = "this")
  public void setFirstAndSecond(final F first, final S second) {
    this.first = first;
    this.second = second;
  }

  /** Reset both values to null. */
  public void reset() {
    this.deleteFirst();
    this.deleteSecond();
  }

  /** Reset the first value to null. */
  @Contract(mutates = "this")
  public void deleteFirst() {
    this.first = null;
  }

  /** Reset the second value to null. */
  @Contract(mutates = "this")
  public void deleteSecond() {
    this.second = null;
  }

  /**
   * Returns the second value.
   *
   * @return the second value
   */
  @Contract(pure = true)
  public S getSecond() {
    return this.second;
  }

  /**
   * Use to set the second value.
   *
   * @param second second value
   */
  @Contract(mutates = "this")
  public void setSecond(final S second) {
    this.second = second;
  }

  /**
   * Returns true if both values are not null.
   *
   * @return true if both values are not null
   */
  @Contract(pure = true)
  public boolean isNotEmpty() {
    return !isEmpty();
  }

  /**
   * Returns true if both values are null.
   *
   * @return true if both values are null
   */
  @Contract(pure = true)
  public boolean isEmpty() {
    return this.first == null && this.second == null;
  }

  /**
   * Check Pair not have the first value.
   *
   * @return true if Pair has no first value
   */
  @Contract(pure = true)
  public boolean notHasFirst() {
    return !this.hasFirst();
  }

  /**
   * Check Pair has the first value.
   *
   * @return true if Pair has first value
   */
  @Contract(pure = true)
  public boolean hasFirst() {
    return this.first != null;
  }

  /**
   * Check Pair not have the second value.
   *
   * @return true if Pair has no second value
   */
  @Contract(pure = true)
  public boolean notHasSecond() {
    return !this.hasSecond();
  }

  /**
   * Check Pair has the second value.
   *
   * @return true if Pair has second value
   */
  @Contract(pure = true)
  public boolean hasSecond() {
    return this.second != null;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(first).append(second).toHashCode();
  }

  @Override
  @Contract(value = "null -> false", pure = true)
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Pair<?, ?> pair = (Pair<?, ?>) o;

    return new EqualsBuilder().append(first, pair.first).append(second, pair.second).isEquals();
  }

  @Override
  @Contract(pure = true)
  public @NotNull String toString() {
    return "Pair{" + "first=" + first + ", second=" + second + '}';
  }
}
