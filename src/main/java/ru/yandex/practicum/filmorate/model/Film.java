package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Set<Long> likes = new HashSet<>();
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Integer rate = 0;

    // Добавляем лайк к фильму с id
    public void addLikes(Long id) {
        this.likes.add(id);
    }

    // Суммируем рейтинг (рейтмнг зависит от колличества лайков)
   public void setRate() {
        this.rate = rate + 1;
   }
}
