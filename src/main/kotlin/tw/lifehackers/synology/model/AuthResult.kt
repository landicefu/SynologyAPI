package tw.lifehackers.synology.model

data class AuthResult private constructor(
        private val data: Data?,
        val success: Boolean
) {
    val sid get() = data?.sid
}

private data class Data(
        val sid: String
)