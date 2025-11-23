package com.example.vinilos.data.model

data class Vinyl(
    val id: Int,

    val name: String,

    val artist: String,

    val albumArt: String,

    val price: Double,

    val description: String
)

object SampleVinyls {
    val vinyls = listOf(
        Vinyl(1, "Dynamo", "Soda Stereo", "https://i.pinimg.com/originals/43/61/41/43614103df5cfc0b34d3390e3cab10ca.jpg", 20000.0,  "Un álbum experimental y atmosférico donde Soda Stereo explora nuevas texturas sonoras, consolidando su legado en el rock latinoamericano."),
        Vinyl(2, "Thriller", "Michael Jackson", "https://preview.redd.it/80rt2zjgjfx41.jpg?width=960&crop=smart&auto=webp&s=f10b4db33a50f324d870b8b4c55ee73488e78926", 20000.0, "El disco más vendido de todos los tiempos. Incluye 'Beat It', 'Billie Jean' y el legendario 'Thriller'."),
        Vinyl(3, "Back in Black", "AC/DC", "https://http2.mlstatic.com/cd-ac-dc-back-in-black-D_NQ_NP_726812-MLM25585373943_052017-F.jpg", 20000.0, "El álbum más poderoso de AC/DC, un tributo a Bon Scott lleno de riffs inolvidables y energía pura."),
        Vinyl(4, "Muerte", "Canserbero", "https://tse3.mm.bing.net/th/id/OIP.8Nfymo9z-iT-iZmvdAK2xgHaHa?pid=Api&P=0&h=180", 20000.0, "Una obra profunda y conmovedora del rap latino, donde Canserbero explora emociones intensas, dualidades y reflexiones sobre la vida y la muerte."),
        Vinyl(5, "Greatest Hits", "2Pac", "https://is3-ssl.mzstatic.com/image/thumb/Music115/v4/aa/07/db/aa07db20-0b14-d09c-ead7-5b9e3680cb76/00602527051840.rgb.jpg/1200x1200bb.jpg", 20000.0, "El mejor recopilatorio del legendario 2Pac. Una colección llena de mensajes sociales, emociones reales y flow inmortal."),
        Vinyl(6, "Marshall Mathers LP", "Eminem", "https://www.udiscovermusic.com/wp-content/uploads/2019/05/Eminem-The-Marshall-Mathers-LP.jpg", 20000.0, "El álbum más intenso y personal de Eminem. Un clásico del hip-hop lleno de crudeza, técnica, storytelling e impacto cultural.")
    )
}