package listener

/**
 * desc: 下载监听
 */
interface UpdateDownloadListener {

    /**
     * 开始下载
     */
    fun onStart()

    /**
     * 下载中
     * @param progress 进度 0 - 100
     */
    fun onDownload(progress: Int)

    /**
     * 下载完成
     */
    fun onFinish()

    /**
     * 下载错误
     */
    fun onError(e: Throwable)
}