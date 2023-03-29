apply {
    from("$rootDir/buildConfig/settings-helper.gradle")
}

val includeIfEnabled = extra.get("includeIfEnabled") as groovy.lang.Closure<*>
val includeAlways = extra.get("includeAlways") as groovy.lang.Closure<*>

includeAlways(":app")
includeIfEnabled(":core")
includeIfEnabled(":core-ui")
includeIfEnabled(":core-entity")
includeIfEnabled(":core-navigation")
includeIfEnabled(":api-splash")
includeIfEnabled(":api-auth")
includeIfEnabled(":feature-auth")
