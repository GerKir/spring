package com.gerkir.spring.controller;

import com.gerkir.spring.entity.Categories;
import com.gerkir.spring.entity.News;
import com.gerkir.spring.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepo newsRepo;

    @GetMapping
    public String getAllNews(Map<String, Object> model) {
        model.put("categories", Categories.values());
        model.put("news", newsRepo.findAll());
        return "news";
    }

    @PostMapping
    public String getFilteredNews(@RequestParam(name = "categories", required = false) String[] categories,
                                  @RequestParam(name = "text", required = false) String text,
                                  Map<String, Object> model) {
        model.put("categories", Categories.values());
        if (categories == null) {
            model.put("news", newsRepo.findByTitleIgnoreCaseContainingOrTextIgnoreCaseContaining(text, text));
            return "news";
        } else {
            Set<Categories> categoriesSet = new HashSet<>();
            for (String category : categories) {
                categoriesSet.add(Categories.valueOf(category));
            }
            Iterable<News> temp = newsRepo.findByTitleIgnoreCaseContainingOrTextIgnoreCaseContaining(text, text);
            HashSet<News> news = new HashSet<>();
            for (News aNews : temp) {
                for (Categories category : categoriesSet) {
                    if (aNews.getCategories().contains(category)) news.add(aNews);
                }
            }
            model.put("news", news);
            return "news";
        }
    }

    @GetMapping("/add")
    public String add(Map<String, Object> model) {
        model.put("message", "Добавьте новость");
        model.put("categories", Categories.values());
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam String text,
                      @RequestParam(name = "categories", required = false) String[] categories,
                      Map<String, Object> model) {
        model.put("categories", Categories.values());

        if (title.isEmpty() || text.isEmpty() || categories == null) {
            model.put("message", "Заполните все поля и выберите категорию");
            return "add";
        } else {
            Set<Categories> categoriesSet = new HashSet<>();
            for (String category : categories) {
                categoriesSet.add(Categories.valueOf(category));
            }

            News news = new News(title, text, categoriesSet);
            newsRepo.save(news);

            model.put("message", "Новость добавлена");
            return "add";
        }
    }

    @GetMapping("edit")
    public String edit(@RequestParam long id, Map<String, Object> model) {
        model.put("categories", Categories.values());

        if (!model.containsKey("message"))
            model.put("message", "Измените данные");

        News news = newsRepo.findById(id);
        model.put("news", news);
        return "edit";
    }

    @PostMapping("edit")
    public String edit(@RequestParam long id,
                       @RequestParam String title,
                       @RequestParam String text,
                       @RequestParam(name = "categories", required = false) String[] categories,
                       Map<String, Object> model) {
        model.put("categories", Categories.values());
        if (title.isEmpty() || text.isEmpty() || categories == null) {
            model.put("message", "Заполните все поля и выберите категорию");
            return edit(id, model);
        } else {
            Set<Categories> categoriesSet = new HashSet<>();
            for (String category : categories) {
                categoriesSet.add(Categories.valueOf(category));
            }
            News news = new News(title, text, categoriesSet);
            news.setId(id);

            newsRepo.deleteById(id);
            newsRepo.save(news);

            model.put("news", news);
            return "redirect:/news";
        }
    }

    @PostMapping("delete")
    public String delete(@RequestParam long id, Map<String, Object> model) {
        newsRepo.deleteById(id);
        return "redirect:/news";
    }
}
