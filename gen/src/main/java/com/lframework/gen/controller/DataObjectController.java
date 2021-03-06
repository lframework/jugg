package com.lframework.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.FileUtil;
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
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = "????????????")
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
   * ?????????????????????????????????
   */
  @Value("${spring.servlet.multipart.location}")
  private String location;

  @ApiOperation("????????????????????????")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryDataObjectBo>> query(@Valid QueryDataObjectVo vo) {

    PageResult<DataObjectDto> pageResult = dataObjectService.query(vo);
    List<DataObjectDto> datas = pageResult.getDatas();
    List<QueryDataObjectBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryDataObjectBo::new).collect(Collectors.toList());
      for (QueryDataObjectBo result : results) {
        UserDto createBy = userService.findById(result.getCreateBy());
        UserDto updateBy = userService.findById(result.getUpdateBy());
        result.setCreateBy(createBy.getName());
        result.setUpdateBy(updateBy.getName());
      }
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("??????ID??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetDataObjectBo> get(@NotBlank(message = "ID???????????????") String id) {

    DataObjectDto data = dataObjectService.findById(id);

    return InvokeResultBuilder.success(new GetDataObjectBo(data));
  }

  @ApiOperation("??????")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateDataObjectVo vo) {

    dataObjectService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateDataObjectVo vo) {

    dataObjectService.update(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????ID??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID???????????????") String id) {

    dataObjectService.delete(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("????????????")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID???????????????") List<String> ids) {

    dataObjectService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("????????????")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID???????????????") List<String> ids) {

    dataObjectService.batchEnable(ids, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("????????????")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID???????????????") List<String> ids) {

    dataObjectService.batchUnable(ids, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("????????????????????????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/generate")
  public InvokeResult<DataObjectGenerateBo> getGenerate(@NotNull(message = "ID???????????????") String id) {

    DataObjectGenerateDto data = dataObjectService.getGenerateById(id);

    DataObjectGenerateBo result = new DataObjectGenerateBo(data);

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation("????????????????????????")
  @PatchMapping("/generate")
  public InvokeResult<Void> updateGenerate(@Valid @RequestBody UpdateDataObjectGenerateVo vo) {

    dataObjectService.updateGenerate(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/preview")
  public InvokeResult<Map<String, String>> preView(@NotNull(message = "ID???????????????") String id) {

    Generator generator = Generator.getInstance(id);
    List<GenerateDto> datas = generator.generateAll();
    Map<String, String> result = new LinkedHashMap<>();
    for (GenerateDto data : datas) {
      result.put(data.getFileName(), data.getContent());
    }

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation("??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/download")
  public void download(@NotNull(message = "ID???????????????") String id) {

    String fileLocation = location.endsWith(File.separator) ? location : location + File.separator;
    String filePath = fileLocation + IdUtil.getUUID() + File.separator;

    Generator generator = Generator.getInstance(id);

    List<GenerateDto> datas = generator.generateAll();
    for (GenerateDto data : datas) {

      File file = FileUtil.file(filePath + data.getPath() + File.separator + data.getFileName());
      FileUtil.writeString(data.getContent(), file, StandardCharsets.UTF_8);
    }

    File zipFile = ZipUtil.zip(filePath, fileLocation + IdUtil.getUUID() + ".zip", false);

    ResponseUtil.download(zipFile);
  }
}
