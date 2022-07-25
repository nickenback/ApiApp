package jp.techacademy.kenji.apiapp

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class FavoriteShop: RealmObject(), Serializable {
    @PrimaryKey
    var id: String=""
    var imageUrl:String=""
    var name: String= ""
    var url: String=""
    var address: String=""
    var couponUrlsSP: String=""
    var couponUrlsPC: String=""

//    var couponUrls: List<CouponUrls>? = null



    companion object{
        fun findAll(): List<FavoriteShop> =
            Realm.getDefaultInstance().use{ realm ->
                realm.where(FavoriteShop::class.java)
                    .findAll().let {
                        realm.copyFromRealm(it)
                    }
            }

        fun findBy(id: String): FavoriteShop? =
            Realm.getDefaultInstance().use{ realm ->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        fun insert(favoriteShop: FavoriteShop) =
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(favoriteShop)
            }

        fun delete(id: String) =
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.also { deleteShop ->
                        realm.executeTransaction {
                            deleteShop.deleteFromRealm()
                        }
                    }
            }
    }
}