import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LigneCmdClientService } from '../service/ligne-cmd-client.service';

import { LigneCmdClientComponent } from './ligne-cmd-client.component';

describe('LigneCmdClient Management Component', () => {
  let comp: LigneCmdClientComponent;
  let fixture: ComponentFixture<LigneCmdClientComponent>;
  let service: LigneCmdClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LigneCmdClientComponent],
    })
      .overrideTemplate(LigneCmdClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneCmdClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneCmdClientService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ligneCmdClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
