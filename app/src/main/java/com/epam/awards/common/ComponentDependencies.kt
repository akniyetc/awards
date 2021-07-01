package com.epam.awards.common

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

// This is used to break cyclic dependencies between gradle modules that contain an activity and
// gradle modules that contain fragments hosted on that activity.
// From:
// https://github.com/Tagakov/modules-and-dagger
// https://www.youtube.com/watch?v=pMEAD6jjbaI
// Also this talk explains a different approach: https://www.youtube.com/watch?v=iwjXqRlEevg&t=1693s


/**
 * Marker for all dagger components. When any dependency is needed, it will be searched by
 * [ComponentDependencies] inside of [ComponentDependenciesProvider].
 */
interface ComponentDependencies

/**
 * @return the corresponding [ComponentDependencies] from the [ComponentDependenciesProvider] map.
 * @throws [IllegalStateException] if neither the activity nor any parent fragment of the fragment
 * implements [HasComponentDependencies].
 */
inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T {
    return findComponentDependenciesProvider()[T::class.java] as T
}

typealias ComponentDependenciesProvider =
        Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

/**
 * Provider interface that must be implemented by the activity or application which contains all the
 * provided dependencies.
 */
interface HasComponentDependencies {
    val dependencies: ComponentDependenciesProvider
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

fun Fragment.findComponentDependenciesProvider(): ComponentDependenciesProvider {
    var current: Fragment? = parentFragment
    while (current !is HasComponentDependencies?) {
        current = current?.parentFragment
    }

    val hasDaggerProviders = current ?: when (activity) {
        is HasComponentDependencies -> activity as HasComponentDependencies
        else -> throw IllegalStateException("Can not find suitable dagger provider for $this")
    }
    return hasDaggerProviders.dependencies
}
