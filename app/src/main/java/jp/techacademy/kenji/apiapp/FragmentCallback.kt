package jp.techacademy.kenji.apiapp

interface FragmentCallback {
//    fun onClickItem(url: String)
    fun onClickItem(shop:FavoriteShop)


    fun onAddFavorite(shop: Shop)

    fun onDeleteFavorite(id: String)
}