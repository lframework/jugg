package com.lframework.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.FileUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ZipUtil;
import com.lframework.gen.bo.dataobj.DataObjectGenerateBo;
import com.lframework.gen.bo.dataobj.GetDataObjectBo;
import com.lframework.gen.bo.dataobj.QueryDataObjectBo;
import com.lframework.gen.dto.dataobj.DataObjectDto;
import com.lframework.gen.dto.dataobj.DataObjectGenerateDto;
import com.lframework.gen.dto.generate.GenerateDto;
import com.lframework.gen.generate.Generator;
import com.lframework.gen.service.IDataObjectService;
import com.lframework.gen.vo.dataobj.CreateDataObjectVo;
import com.lframework.gen.vo.dataobj.QueryDataObjectVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectGenerateVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ResponseUtil;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/gen/dataobj")
public class DataObjectController extends DefaultBaseController {

  @Autowired
  private IDataObjectService dataObjectService;

  @Autowired
  private IUserService userService;

  /**
   * 上传文件的临时文件目录
   */
  @Value("${spring.servlet.multipart.location}")
  private String location;

  @GetMapping("/query")
  public InvokeResult query(@Valid QueryDataObjectVo vo) {

    PageResult<DataObjectDto> pageResult = dataObjectService.query(vo);
    List<DataObjectDto> datas = pageResult.getDatas();
    if (CollectionUtil.isNotEmpty(datas)) {
      List<QueryDataObjectBo> results = datas.stream().map(QueryDataObjectBo::new)
          .collect(Collectors.toList());
      for (QueryDataObjectBo result : results) {
        UserDto createBy = userService.getById(result.getCreateBy());
        UserDto updateBy = userService.getById(result.getUpdateBy());
        result.setCreateBy(createBy.getName());
        result.setUpdateBy(updateBy.getName());
      }

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    DataObjectDto data = dataObjectService.getById(id);

    return InvokeResultBuilder.success(new GetDataObjectBo(data));
  }

  @PostMapping
  public InvokeResult create(@Valid CreateDataObjectVo vo) {

    dataObjectService.create(vo);

    return InvokeResultBuilder.success();
  }

  @PutMapping
  public InvokeResult update(@Valid UpdateDataObjectVo vo) {

    dataObjectService.update(vo);

    return InvokeResultBuilder.success();
  }

  @DeleteMapping
  public InvokeResult delete(@NotBlank(message = "ID不能为空！") String id) {

    dataObjectService.delete(id);

    return InvokeResultBuilder.success();
  }

  @DeleteMapping("/batch")
  public InvokeResult batchDelete(@RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    dataObjectService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }

  @PatchMapping("/enable/batch")
  public InvokeResult batchEnable(@RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    dataObjectService.batchEnable(ids, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  @PatchMapping("/unable/batch")
  public InvokeResult batchUnable(@RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    dataObjectService.batchUnable(ids, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  @GetMapping("/generate")
  public InvokeResult getGenerate(@NotNull(message = "ID不能为空！") String id) {

    DataObjectGenerateDto data = dataObjectService.getGenerateById(id);

    DataObjectGenerateBo result = new DataObjectGenerateBo(data);

    return InvokeResultBuilder.success(result);
  }

  @PatchMapping("/generate")
  public InvokeResult updateGenerate(@Valid @RequestBody UpdateDataObjectGenerateVo vo) {

    dataObjectService.updateGenerate(vo);

    return InvokeResultBuilder.success();
  }

  @GetMapping("/preview")
  public InvokeResult preView(String id) {

    Generator generator = Generator.getInstance(id);
    List<GenerateDto> datas = generator.generateAll();
    Map<String, String> result = new LinkedHashMap<>();
    for (GenerateDto data : datas) {
      result.put(data.getFileName(), data.getContent());
    }

    return InvokeResultBuilder.success(result);
  }

  @GetMapping("/download")
  public void download(String id) {

    String fileLocation = location.endsWith(File.separator) ? location : location + File.separator;
    String filePath = fileLocation + IdUtil.getId() + File.separator;

    Generator generator = Generator.getInstance(id);

    List<GenerateDto> datas = generator.generateAll();
    for (GenerateDto data : datas) {

      File file = FileUtil.file(filePath + data.getPath() + File.separator + data.getFileName());
      FileUtil.writeString(data.getContent(), file, StandardCharsets.UTF_8);
    }

    File zipFile = ZipUtil.zip(filePath, fileLocation + IdUtil.getId() + ".zip", false);

    ResponseUtil.download(zipFile);
  }
}
