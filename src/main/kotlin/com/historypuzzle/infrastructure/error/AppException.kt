sealed class AppException(val code: Int, override val message: String) : Throwable()
class PreconditionException(message: String) : AppException(400, message)