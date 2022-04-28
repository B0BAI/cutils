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

package value;

import art.cutils.value.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PairTest {

  @Test
  void testPairWithInitialValueOnCreation() {
    final Pair<Integer, String> pair = Pair.of(1, "Two");
    assertEquals(1, pair.getFirst());
    assertEquals("Two", pair.getSecond());

    assertTrue(pair.hasFirst());
    assertTrue(pair.hasSecond());

    assertTrue(pair.isNotEmpty());
  }

  @Test
  void testInitialEmptyPair() {
    final Pair pair = Pair.empty();

    assertNull(pair.getFirst());
    assertNull(pair.getSecond());

    assertFalse(pair.hasFirst());
    assertFalse(pair.hasSecond());

    assertFalse(pair.isNotEmpty());

    assertTrue(pair.notHasFirst());
    assertTrue(pair.notHasSecond());

    assertTrue(pair.isEmpty());

    pair.setFirst(1);
    pair.setSecond("Two");

    assertEquals(1, pair.getFirst());
    assertEquals("Two", pair.getSecond());

    assertTrue(pair.hasFirst());
    assertTrue(pair.hasSecond());

    assertTrue(pair.isNotEmpty());
  }

  @Test
  void testPauseContracts() {
    final Pair<Integer, String> pair = Pair.of(1, "Two");
    final Pair<String, String> pair2 = Pair.of("One", "Two");

    assertNotEquals(pair, pair2);

    assertFalse(pair.equals(null) && pair.equals(new Object()));

    assertEquals(pair, Pair.of(1, "Two"));

    assertEquals(pair.hashCode(), Pair.of(1, "Two").hashCode());

    assertNotEquals(pair.hashCode(), pair2.hashCode());

    assertNotSame(pair.toString(), pair2.toString());
  }

  @Test
  void testPairWithNullValues() {
    final Pair<Integer, String> pair = Pair.of(1, null);
    assertEquals(1, pair.getFirst());
    assertNull(pair.getSecond());

    assertTrue(pair.hasFirst());
    assertFalse(pair.hasSecond());

    assertTrue(pair.isNotEmpty());

    pair.setFirst(null);
    pair.setSecond("Two");

    assertNull(pair.getFirst());
    assertEquals("Two", pair.getSecond());

    assertFalse(pair.hasFirst());
    assertTrue(pair.hasSecond());

    assertTrue(pair.isNotEmpty());
  }

  @Test
  void testResetAndDelete() {
    final Pair<Integer, String> pair = Pair.of(1, "Two");
    pair.reset();
    assertNull(pair.getFirst());
    assertNull(pair.getSecond());
    assertFalse(pair.hasFirst());
    assertFalse(pair.hasSecond());
    assertFalse(pair.isNotEmpty());
    assertTrue(pair.isEmpty());

    pair.setFirstAndSecond(3, "Four");

    assertEquals(3, pair.getFirst());
    assertEquals("Four", pair.getSecond());

    assertTrue(pair.hasFirst());
    assertTrue(pair.hasSecond());

    assertTrue(pair.isNotEmpty());
  }
}
