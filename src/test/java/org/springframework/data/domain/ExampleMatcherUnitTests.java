/*
 * Copyright 2016 the original author or authors.
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
package org.springframework.data.domain;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.data.domain.ExampleMatcher.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.ExampleMatcher.NullHandler;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

/**
 * Unit test for {@link ExampleMatcher}.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @soundtrack K2 - Der Berg Ruft (Club Mix)
 */
public class ExampleMatcherUnitTests {

	ExampleMatcher matcher;

	@Before
	public void setUp() throws Exception {
		matcher = matching();
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void defaultStringMatcherShouldReturnDefault() throws Exception {
		assertThat(matcher.getDefaultStringMatcher()).isEqualTo(StringMatcher.DEFAULT);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoreCaseShouldReturnFalseByDefault() throws Exception {
		assertThat(matcher.isIgnoreCaseEnabled()).isFalse();
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoredPathsIsEmptyByDefault() throws Exception {
		assertThat(matcher.getIgnoredPaths()).isEmpty();
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void nullHandlerShouldReturnIgnoreByDefault() throws Exception {
		assertThat(matcher.getNullHandler()).isEqualTo(NullHandler.IGNORE);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void ignoredPathsIsNotModifiable() throws Exception {
		matcher.getIgnoredPaths().add("¯\\_(ツ)_/¯");
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoreCaseShouldReturnTrueWhenIgnoreCaseEnabled() throws Exception {

		matcher = matching().withIgnoreCase();

		assertThat(matcher.isIgnoreCaseEnabled()).isTrue();
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoreCaseShouldReturnTrueWhenIgnoreCaseSet() throws Exception {

		matcher = matching().withIgnoreCase(true);

		assertThat(matcher.isIgnoreCaseEnabled()).isTrue();
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void nullHandlerShouldReturnInclude() throws Exception {

		matcher = matching().withIncludeNullValues();

		assertThat(matcher.getNullHandler()).isEqualTo(NullHandler.INCLUDE);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void nullHandlerShouldReturnIgnore() throws Exception {

		matcher = matching().withIgnoreNullValues();

		assertThat(matcher.getNullHandler()).isEqualTo(NullHandler.IGNORE);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void nullHandlerShouldReturnConfiguredValue() throws Exception {

		matcher = matching().withNullHandler(NullHandler.INCLUDE);

		assertThat(matcher.getNullHandler()).isEqualTo(NullHandler.INCLUDE);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoredPathsShouldReturnCorrectProperties() throws Exception {

		matcher = matching().withIgnorePaths("foo", "bar", "baz");

		assertThat(matcher.getIgnoredPaths()).contains("foo", "bar", "baz");
		assertThat(matcher.getIgnoredPaths()).hasSize(3);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void ignoredPathsShouldReturnUniqueProperties() throws Exception {

		matcher = matching().withIgnorePaths("foo", "bar", "foo");

		assertThat(matcher.getIgnoredPaths()).contains("foo", "bar");
		assertThat(matcher.getIgnoredPaths()).hasSize(2);
	}

	/**
	 * @see DATACMNS-810
	 */
	@Test
	public void withCreatesNewInstance() throws Exception {

		matcher = matching().withIgnorePaths("foo", "bar", "foo");
		ExampleMatcher configuredExampleSpec = matcher.withIgnoreCase();

		assertThat(matcher).isNotEqualTo(sameInstance(configuredExampleSpec));
		assertThat(matcher.getIgnoredPaths()).hasSize(2);
		assertThat(matcher.isIgnoreCaseEnabled()).isFalse();

		assertThat(configuredExampleSpec.getIgnoredPaths()).hasSize(2);
		assertThat(configuredExampleSpec.isIgnoreCaseEnabled()).isTrue();
	}

	/**
	 * @see DATACMNS-879
	 */
	@Test
	public void defaultMatcherRequiresAllMatching() {

		assertThat(matching().isAllMatching(), is(true));
		assertThat(matching().isAnyMatching(), is(false));
	}

	/**
	 * @see DATACMNS-879
	 */
	@Test
	public void allMatcherRequiresAllMatching() {

		assertThat(matchingAll().isAllMatching(), is(true));
		assertThat(matchingAll().isAnyMatching(), is(false));
	}

	/**
	 * @see DATACMNS-879
	 */
	@Test
	public void anyMatcherYieldsAnyMatching() {

		assertThat(matchingAny().isAnyMatching(), is(true));
		assertThat(matchingAny().isAllMatching(), is(false));
	}

	static class Person {

		String firstname;
	}
}
