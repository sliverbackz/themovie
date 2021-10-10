package co.zmt.themovie.repository.entitymapper

interface UnidirectionalMap<F, T> {
    fun map(item: F): T
}
